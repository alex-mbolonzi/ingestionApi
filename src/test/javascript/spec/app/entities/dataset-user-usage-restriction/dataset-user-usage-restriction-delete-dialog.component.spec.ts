import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetUserUsageRestrictionDeleteDialogComponent } from 'app/entities/dataset-user-usage-restriction/dataset-user-usage-restriction-delete-dialog.component';
import { DatasetUserUsageRestrictionService } from 'app/entities/dataset-user-usage-restriction/dataset-user-usage-restriction.service';

describe('Component Tests', () => {
  describe('DatasetUserUsageRestriction Management Delete Component', () => {
    let comp: DatasetUserUsageRestrictionDeleteDialogComponent;
    let fixture: ComponentFixture<DatasetUserUsageRestrictionDeleteDialogComponent>;
    let service: DatasetUserUsageRestrictionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetUserUsageRestrictionDeleteDialogComponent],
      })
        .overrideTemplate(DatasetUserUsageRestrictionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetUserUsageRestrictionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetUserUsageRestrictionService);
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
