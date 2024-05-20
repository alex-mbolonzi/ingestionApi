import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetRoleDetailsDeleteDialogComponent } from 'app/entities/dataset-role-details/dataset-role-details-delete-dialog.component';
import { DatasetRoleDetailsService } from 'app/entities/dataset-role-details/dataset-role-details.service';

describe('Component Tests', () => {
  describe('DatasetRoleDetails Management Delete Component', () => {
    let comp: DatasetRoleDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<DatasetRoleDetailsDeleteDialogComponent>;
    let service: DatasetRoleDetailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetRoleDetailsDeleteDialogComponent],
      })
        .overrideTemplate(DatasetRoleDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetRoleDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetRoleDetailsService);
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
