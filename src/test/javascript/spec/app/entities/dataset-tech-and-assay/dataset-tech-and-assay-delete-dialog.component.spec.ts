import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetTechAndAssayDeleteDialogComponent } from 'app/entities/dataset-tech-and-assay/dataset-tech-and-assay-delete-dialog.component';
import { DatasetTechAndAssayService } from 'app/entities/dataset-tech-and-assay/dataset-tech-and-assay.service';

describe('Component Tests', () => {
  describe('DatasetTechAndAssay Management Delete Component', () => {
    let comp: DatasetTechAndAssayDeleteDialogComponent;
    let fixture: ComponentFixture<DatasetTechAndAssayDeleteDialogComponent>;
    let service: DatasetTechAndAssayService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetTechAndAssayDeleteDialogComponent],
      })
        .overrideTemplate(DatasetTechAndAssayDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetTechAndAssayDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetTechAndAssayService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        }),
      ));
    });
  });
});
