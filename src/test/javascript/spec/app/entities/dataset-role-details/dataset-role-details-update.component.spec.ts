import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetRoleDetailsUpdateComponent } from 'app/entities/dataset-role-details/dataset-role-details-update.component';
import { DatasetRoleDetailsService } from 'app/entities/dataset-role-details/dataset-role-details.service';
import { DatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';

describe('Component Tests', () => {
  describe('DatasetRoleDetails Management Update Component', () => {
    let comp: DatasetRoleDetailsUpdateComponent;
    let fixture: ComponentFixture<DatasetRoleDetailsUpdateComponent>;
    let service: DatasetRoleDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetRoleDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DatasetRoleDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetRoleDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetRoleDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetRoleDetails(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetRoleDetails();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
